package com.cumego.poligon.spock.repository

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.unitils.dbunit.annotation.DataSet
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy
import spock.lang.Specification
import spock.unitils.UnitilsSupport

@ContextConfiguration(locations = '/META-INF/spring/applicationContext.xml')
@UnitilsSupport //Unitils tests are transactional by default
class BookRepositoryUnitilsTest extends Specification {

    private static final Logger logger = LoggerFactory.getLogger(BookRepositoryUnitilsTest)

    @Autowired
    def BookRepository repository

    def 'book repository bean should be injected'() {
        expect:
        repository != null
    }

    @DataSet(value = "/books.xml", loadStrategy = CleanInsertLoadStrategy)
    def 'should load have books loaded through DbUnit'() {
        when:
        def books = repository.findAll()

        then:
        logger.info("loaded books: {}", books)
        books*.id == [1, 2]
    }
}