package uk.co.mcv.api.v1

import uk.co.mcv.BetterRestfulController
import uk.co.mcv.model.Model


/**
 * A readonly controller to get list of all available Models
 *
 */

class ModelController extends  BetterRestfulController{
    static namespace = "v1"

    ModelController(){
        super(Model,true)
    }
}