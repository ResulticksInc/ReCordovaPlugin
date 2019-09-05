var exec = require('cordova/exec');

module.exports.userRegister = function(arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'userRegister', [ arg0 ]);
};

module.exports.customEvent = function(arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'customEvent', [ arg0 ]);
};

module.exports.locationUpdate = function(arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'locationUpdate', [ arg0 ]);
};

module.exports.screenNavigation = function(arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'screenNavigation', [ arg0 ]);
};
