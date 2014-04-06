package pages

import geb.Page

/**
 * Created by soheil on 06/04/2014.
 */
class DataElementPage extends Page{

    static url = "dataElement/index"

    static at = {
        url == "dataElement/index" &&
        title == "DataElement List"
    }

}

