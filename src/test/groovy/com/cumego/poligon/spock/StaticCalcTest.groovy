package com.cumego.poligon.spock

import org.easymock.EasyMock
import org.junit.Rule
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.rule.PowerMockRule
import org.powermock.api.easymock.PowerMock
import spock.lang.Specification

@PrepareForTest(StaticCalc)
class StaticCalcTest extends Specification {

    // parent runner is Spock's Sputnik, so we bootstrap PowerMock using JUnit rule:
    @Rule PowerMockRule rule = new PowerMockRule()

    def 'should use mock instead of original static method'() {
        given:
        def int x = 4
        def int y = 2
        PowerMock.mockStatic(StaticCalc)
        EasyMock.expect(StaticCalc.add(x, y)).andReturn(42).times(1)
        PowerMock.replay(StaticCalc)

        when:
        def result = StaticCalc.add(x, y)

        then:
        result == 42
        // verify that it has been called
        PowerMock.verify(StaticCalc)
    }
}