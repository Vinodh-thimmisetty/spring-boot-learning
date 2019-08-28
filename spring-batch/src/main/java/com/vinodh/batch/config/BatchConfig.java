package com.vinodh.batch.config;

import com.vinodh.batch.entity.UserInfo;
import com.vinodh.batch.entity.UserInfoRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private DataSource dataSource;

    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public Job reportJob(
            @Qualifier("sample") Step sample, // Load Sample Data into DB
            @Qualifier("export") Step export, // Read Data and Convert to CSV
            @Qualifier("email") Step email // Send an Email to Admin.
    ) {
        return this.jobBuilderFactory.get("reportJob")
                .incrementer(jobParameters -> new JobParametersBuilder()
                        .addDate("startDate", new Date())
                        .toJobParameters())
                .start(sample)
                .next(export)
                .next(email)
                .preventRestart()
                .build();
    }

    @Bean
    protected Step export(
            @Qualifier("dbReader") ItemReader<UserInfo> dbReader, // read from DB
            @Qualifier("convertDate") ItemProcessor<UserInfo, UserInfo> processor, // Convert the Date based on Time Zone.
            @Qualifier("writer") ItemWriter<UserInfo> writer // Write to CSV/Excel
    ) {
        return this.stepBuilderFactory.get("export")
                .<UserInfo, UserInfo>chunk(5)
                .reader(dbReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<UserInfo> dbReader() {
        JdbcCursorItemReader<UserInfo> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT name, email, registration_time FROM user_info");
        reader.setRowMapper(new UserInfoRowMapper());
        return reader;
    }

    @Bean
    public ItemProcessor<UserInfo, UserInfo> convertDate() {
        return (user) -> {
            LocalDateTime formattedDate = LocalDateTime.parse(user.getRegistrationTime());
            ZonedDateTime utcDate = ZonedDateTime.of(formattedDate, ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
            user.setRegistrationTime(utcDate.toString());
            return user;
        };
    }

    @Bean
    public ItemWriter<UserInfo> writer() {
        Resource outputResource = new FileSystemResource("users.csv");
        FlatFileItemWriter<UserInfo> writer = new FlatFileItemWriter<>();
        writer.setResource(outputResource);
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<UserInfo>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<UserInfo>() {{
                setNames(new String[]{"name", "email", "registrationTime"});
            }});
        }});
        return writer;
    }


    class UserInfoRowMapper implements RowMapper<UserInfo> {
        @Override
        public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserInfo user = new UserInfo();
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setRegistrationTime(rs.getString("registration_time"));
            System.out.println("======> Row Mapping ====" + user.toString());
            return user;
        }
    }


    @Bean
    protected Step email() {
        return this.stepBuilderFactory.get("sendEmail")
                .tasklet(emailTask())
                .build();
    }


    @Bean
    public Tasklet emailTask() {
        return (StepContribution sc, ChunkContext cc) -> {
            System.out.println("=======================================> Email is sent to ADMIN <=======================================");
            return RepeatStatus.FINISHED;
        };
    }

    @Autowired
    UserInfoRepository userInfoRepository;


    @Bean
    protected Step sample() {
        return this.stepBuilderFactory.get("loadSample")
                .tasklet(loadSample())
                .build();
    }

    @Bean
    public Tasklet loadSample() {
        return (StepContribution sc, ChunkContext cc) -> {
            List<UserInfo> sampleData = Stream
                    .iterate(1, n -> n + 1)
                    .limit(20)
                    .map(x -> UserInfo
                            .builder()
                            .name("vinodh-" + x)
                            .email("vin_" + x + "@gmail.com")
                            .registrationTime(LocalDateTime.now().toString())
                            .build())
                    .collect(Collectors.toList());
            userInfoRepository.saveAll(sampleData);
            return RepeatStatus.FINISHED;
        };
    }

}
