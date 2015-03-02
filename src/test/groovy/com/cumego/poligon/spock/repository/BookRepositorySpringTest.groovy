package com.cumego.poligon.spock.repository

import com.cumego.poligon.spock.domain.Book
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(locations = '/META-INF/spring/applicationContext.xml')
class BookRepositorySpringTest extends Specification {

    private static final Logger logger = LoggerFactory.getLogger(BookRepositorySpringTest)

    @Autowired
    def BookRepository repository

    def 'book repository bean should be injected'() {
        expect:
        repository != null
    }

    def 'should add a book'() {
        given:
        def book = new Book(author: "Robert C. Martin", title: "Clean code")

        when:
        //this should be called on transactional service, but for brevity:
        repository.save(book);
        repository.flush()

        then:
        book.id != null
        logger.info("new book: {}", book)
    }
}