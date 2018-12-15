package com.company.demo.beans

import grails.util.Holders
import org.apache.log4j.Logger
import javax.annotation.PostConstruct

import com.company.demo.Car
import com.company.demo.CarService

import grails.plugins.primefaces.GrailsService
import grails.plugins.primefaces.MessageSourceBean

import javax.faces.bean.ManagedBean
import javax.faces.bean.ManagedProperty
import javax.faces.bean.SessionScoped
import javax.faces.context.FacesContext

import org.primefaces.event.SelectEvent
import org.primefaces.model.LazyDataModel

import org.springframework.validation.FieldError

@ManagedBean(name = "carMB")
@SessionScoped
class CarManagedBean implements Serializable {
    Logger log = Logger.getLogger(CarManagedBean.class)
        
    @PostConstruct
    public void init() {
        cars = new CarLazyDataModel(carService)
    }
    
    @ManagedProperty(value = "#{message}")
    MessageSourceBean message
    
    //@GrailsService(name = "carService")
    CarService carService=Holders.grailsApplication.mainContext.getBean 'carService'
    
    Car car
    LazyDataModel<Car> cars

    public void save() {
        log.debug(car)        
        boolean updated = (car.id != null)
        List<FieldError> errors = getCarService().save(car)
        if (errors == null) {
            if (updated == true) {
                message.infoPF("pf.default.updated.message", null, "Car")
            } else {
                message.infoPF("pf.default.created.message", null, "Car")
            }
            reset()
        } else {
            for (FieldError error : errors) {
                message.errorMessagePF("", message.getErrorMessage(error))
            }
        }
    }
    
    public void reset() {
        car = new Car()
    }
    
    //public void delete(Long id) {
    //    getCarService().delete(id)
    //    message.infoPF("pf.default.deleted.message", null, "Car")
    //}

     public void delete() {
            def id=RequestContextHolder.getRequestAttributes().getParams().get("id")
             Long  longId=Long.parseLong(id)
            getCarService().delete(longId)
            message.infoPF("pf.default.deleted.message", null, "Car")
        }

    public void onRowSelect(SelectEvent event) {
        Long id = ((Car) event.getObject()).getId()
        log.info("id = " + id)
        car = getCarService().get(id)
    }

}

