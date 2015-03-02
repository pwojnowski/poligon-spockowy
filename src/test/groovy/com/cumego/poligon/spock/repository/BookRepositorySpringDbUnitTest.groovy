package com.cumego.poligon.spock.repository

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ContextConfiguration(locations = '/META-INF/spring/applicationContext.xml')
@TestExecutionListeners([
        DependencyInjectionTestExecutionListener, DbUnitTestExecutionListener])
class BookRepositorySpringDbUnitTest extends Specification {

    private static final Logger logger = LoggerFactory.getLogger(BookRepositorySpringDbUnitTest)

    @Autowired
    def BookRepository repository

    def 'book repository bean should be injected'() {
        expect:
        repository != null
    }

    //spring-test-dbunit tests are _not_ transactional by default
    @Transactional(readOnly = true)
    @DatabaseSetup("/books.xml")
    def 'should have books loaded through DbUnit'() {
        when:
        def books = repository.findAll()

        then:
        logger.info("loaded books: {}", books)
        books*.id == [1, 2]
    }
}