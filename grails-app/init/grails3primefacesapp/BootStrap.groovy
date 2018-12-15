package grails3primefacesapp

import com.company.demo.*

class BootStrap {

    def init = { servletContext ->
        log.info "BootStrap demo"

        def t1 = System.currentTimeMillis()
        def input
        try {
            if(Anagraphic.list().size()<=0) {
                input = servletContext.getResourceAsStream("/WEB-INF/resources/data.csv")
                // input will be null when using "run-command <command>", so make this robust
                input?.eachLine { line ->
                    def a = new Anagraphic(firstName: line.split(";")[0], surname: line.split(";")[1])
                    //   println a.firstName
                    if (!a.save(flush: true)) {
                        println a.errors
                    }
                }
            }
        } finally {
            if (input)
                input.close()
        }
        def t2 = System.currentTimeMillis()
        def delay = t2 - t1
        log.info "delay = ${delay} [mills]"

        // initialize Car data
        def colors = ['Black', 'White', 'Green', 'Red', 'Blue', 'Orange', 'Silver', 'Yellow', 'Brown', 'Maroon']
        def brands = ['BMW', 'Mercedes', 'Volvo', 'Audi', 'Renault', 'Fiat', 'Volkswagen', 'Honda', 'Jaguar', 'Ford']
        // enumerate all color and brand combinations
        def combinations = [colors, brands].combinations()
        combinations.each{ row ->
            String color = row[0]
            String brand = row[1]
            // initialize a car entry
            int newYear = (int) (Math.random() * 50 + 1960)
            String newID = UUID.randomUUID().toString().substring(0, 8);
            Car car = new Car (carID:newID, year:newYear, color:color, brand:brand)
            car.save(flush:true, failOnError:true)
        }

        println "Total Cars:  " + Car.count()
                
    }
    def destroy = {
    }
}
