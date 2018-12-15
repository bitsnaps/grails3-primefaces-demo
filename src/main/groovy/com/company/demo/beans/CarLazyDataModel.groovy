package com.company.demo.beans
import grails.util.Holders
import org.apache.log4j.Logger

import com.company.demo.Car
import com.company.demo.CarService

import org.primefaces.model.LazyDataModel
import org.primefaces.model.SortOrder

public class CarLazyDataModel extends LazyDataModel<Car> {
    Logger log = Logger.getLogger(CarLazyDataModel.class)
            
    CarService carService
    
    public CarLazyDataModel() {
        this.carService = Holders.grailsApplication.mainContext.getBean 'carService'
    }

    public CarLazyDataModel(CarService carService) {
        this.carService = carService
    }

    @Override
    public List<Car> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) { 
        log.debug("first = " + first + ", pageSize = " + pageSize + ", sortField = " + sortField + ", sortOrder = " + sortOrder + ", filters = " + filters)
        def cars
        Car.withNewSession() {
            if (!sortField) {
                if (filters.size() == 0)
                    cars = carService.list(pageSize, first)
                else
                    cars = carService.filter(filters, pageSize, first)
            } else {
                String order = sortOrder == SortOrder.ASCENDING ? "asc" : "desc"
                if (filters.size() == 0)
                    cars = carService.list(pageSize, first, sortField, order)
                else
                    cars = carService.filter(filters, pageSize, first, sortField, order)
            }
            this.setRowCount(cars.totalCount)
        }
        return cars
    }
}

