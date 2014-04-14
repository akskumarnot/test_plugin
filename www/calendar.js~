var calendar =  {
    enable: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'Calendar', // mapped to our native Java class called "Calendar"
            'enable', // with this action name
            []
        );
    },


	pairedList: function(success,failure){
	 cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'Calendar', // mapped to our native Java class called "Calendar"
            'pairedList', // with this action name
            []
        );	
	}	
}
module.exports = calendar;
