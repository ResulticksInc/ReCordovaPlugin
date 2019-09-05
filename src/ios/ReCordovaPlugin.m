/********* ResulticksPlugin.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface ReCordovaPlugin : CDVPlugin {
  // Member variables go here.
}
- (void)coolMethod:(CDVInvokedUrlCommand*)command;
- (void)userRegister:(CDVInvokedUrlCommand*)command;
- (void)customEvent:(CDVInvokedUrlCommand*)command;
- (void)screenNavigation:(CDVInvokedUrlCommand*)command;
- (void)locationUpdate:(CDVInvokedUrlCommand*)command;
@end

@implementation ReCordovaPlugin

- (void)userRegister:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];
    if (echo != nil && [echo length] > 0) {
        NSLog(@"userRegister %@",echo);
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
}

- (void)customEvent:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];
    
    if (echo != nil && [echo length] > 0) {
        NSLog(@"customEvent %@",echo);
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
}

- (void)screenNavigation:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];
    
    if (echo != nil && [echo length] > 0) {
        NSLog(@"screenNavigation %@",echo);
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
}

- (void)locationUpdate:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];
    
    if (echo != nil && [echo length] > 0) {
        NSLog(@"locationUpdate %@",echo);
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
}


@end
