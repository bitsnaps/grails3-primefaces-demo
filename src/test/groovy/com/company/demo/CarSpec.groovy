package com.company.demo

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CarSpec extends Specification implements DomainUnitTest<Car> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fixed"
            true
    }
}
