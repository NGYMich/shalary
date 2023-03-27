//package com.ngymich.shalary.infrastructure.persistence;
//
//import ch.qos.logback.classic.Level;
//import ch.qos.logback.classic.Logger;
//import ch.qos.logback.classic.spi.LoggingEvent;
//import ch.qos.logback.core.Appender;
//import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
//import com.ngymich.shalary.infrastructure.persistence.salary.SalaryHistoryJpaRepository;
//import com.ngymich.shalary.infrastructure.persistence.salary.SalaryInfosJpaRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//@Transactional
//class NPlusOneQueriesLoggingTest {
//
//    @Autowired
//    private SalaryHistoryJpaRepository salaryHistoryJpaRepository;
//
//    @Mock
//    private Appender mockedAppender;
//
//    @Captor
//    private ArgumentCaptor<LoggingEvent> loggingEventCaptor;
//
//    @BeforeEach
//    public void setup() {
//        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
//        root.addAppender(mockedAppender);
//    }
//
//    @Test
//    void hibernateQueryInterceptor_isDetectingNPlusOneQueriesWhenMissingEagerFetchingOnQuery() {
//        // Fetch the 2 salaryHistory without the authors
//        List<PersistableSalaryHistory> salaryHistory = salaryHistoryJpaRepository.findAll();
//
//        // The getters trigger N+1 queries
//        List<String> names = salaryHistory.stream()
//                .map(message -> message.getUser().getUsername())
//                .collect(Collectors.toList());
//
//        verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
//
//        LoggingEvent loggingEvent = loggingEventCaptor.getAllValues().get(0);
//        assertThat(loggingEvent.getMessage())
//                .contains("N+1 queries detected on a getter of the entity com.yannbriancon.utils.entity.User\n" +
//                        "    at com.yannbriancon.interceptor.NPlusOneQueriesLoggingTest." +
//                        "lambda$hibernateQueryInterceptor_isDetectingNPlusOneQueriesWhenMissingEagerFetchingOnQuery$0");
//        assertThat(Level.ERROR).isEqualTo(loggingEvent.getLevel());
//    }
//}
