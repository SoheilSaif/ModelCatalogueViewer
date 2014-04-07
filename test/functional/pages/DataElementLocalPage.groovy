package pages

import geb.Page

/**
 * Created by soheil on 06/04/2014.
 */
class DataElementLocalPage extends Page{

    static url = "dataElementLocal/index"

    static at = {
        url == "dataElementLocal/index" &&
        title == "DataElementLocal List"
    }

}

