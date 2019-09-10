var exec = require('cordova/exec');

module.exports.userRegister = function (arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'userRegister', [arg0]);
};

module.exports.customEvent = function (arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'customEvent', [arg0]);
};

module.exports.locationUpdate = function (arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'locationUpdate', [arg0]);
};

module.exports.screenNavigation = function (arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'screenNavigation', [arg0]);
};

module.exports.getNotification = function (arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'getNotification', [arg0]);
};

module.exports.deleteNotification = function (arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'deleteNotification', [arg0]);
};

module.exports.notificationPayLoadReceiver = function (arg0, success, error) {
	exec(success, error, 'ReCordovaPlugin', 'notificationPayLoadReceiver', [arg0]);
};