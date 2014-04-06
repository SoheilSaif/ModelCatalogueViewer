package specs

import geb.Browser
import geb.spock.GebReportingSpec
import org.openqa.selenium.Dimension

/**
 * Created by soheil on 06/04/2014.
 */
class DataElementListSpec extends GebReportingSpec{


    def setup(){
        driver.manage().window().setSize(new Dimension(1028, 768))
    }

    def "go to DataElementList home screen and show DataElement list"(){

        when: "At DataElementList"
        to pages.DataElementPage

        then:
        at pages.DataElementPage
        $("a[class='create']").text() == "New DataElement"

    }
}