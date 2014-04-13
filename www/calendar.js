var calendar =  {
    enabled: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'Calendar', // mapped to our native Java class called "Calendar"
            'enabled', // with this action name
            []
        );
    }
}
module.exports = calendar;
