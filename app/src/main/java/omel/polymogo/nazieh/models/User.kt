package omel.polymogo.nazieh.models

/**
 * Created by MrTayyab on 2/24/2018.
 */
class User {


    /// MOdel class
    var name : String? = null
    var phone : String? = null


    constructor(){

    }
    constructor(name: String?, phone: String?) {
        this.name = name
        this.phone = phone

    }
}