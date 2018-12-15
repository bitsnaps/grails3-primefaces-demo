package com.company.demo

import grails.transaction.Transactional
import org.springframework.validation.FieldError

@Transactional
class CarService {

    Car get(Long id) {
        Car.get(id)
    }

    def list() {
        Car.list()
    }

    def list(int max, int offset) {
        log.debug "[list] max = ${max}, offset = ${offset}"
        Car.list(max: max, offset: offset)
    }

    def list(int max, int offset, String sort, String order) {
        log.debug "[list] max = ${max}, offset = ${offset}, sort = ${sort}, order = ${order}"
        Car.list(max: max, offset: offset, sort: sort, order: order)
    }

    boolean delete(Long id) {
        log.debug "[delete] id = ${id}"
        def car = Car.get(id)
        car.delete(flush: true)
    }
	    
    List<FieldError> save(Car car) {
        log.debug "[save] id = ${propertyName?.id}"
        def errors = null
        if (car.save(flush: true)) {
            return null
        } else {
            car.errors.allErrors
        }
    }
    
    def filter(Map filters, int max, int offset) {
        def c = Car.createCriteria()
        c.list (max: max, offset: offset) {
            and {
                filters.each { k, v ->
                    ilike(k, "%${v}%")
                }
            }
        }
    }
    
    def filter(Map filters, int max, int offset, String sort, String order) {
        def c = Car.createCriteria()
        c.list (max: max, offset: offset) {
            and {
                filters.each { k, v ->
                    ilike(k, "%${v}%")
                }
            }
            order(sort, order)
        }
    }

}