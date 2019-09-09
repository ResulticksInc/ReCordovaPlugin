/********* ResulticksPlugin.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface ReCordovaPlugin : CDVPlugin {
  // Member variables go here.
}
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

        NSString uniqueId = [[command.arguments objectAtIndex:0] valueForKey:@"uniqueId"];
        NSString name = [[command.arguments objectAtIndex:0] valueForKey:@"name"];
        NSString email = [[command.arguments objectAtIndex:0] valueForKey:@"email"];
        NSString phone = [[command.arguments objectAtIndex:0] valueForKey:@"phone"];
        NSString age = [[command.arguments objectAtIndex:0] valueForKey:@"age"];
        NSString gender = [[command.arguments objectAtIndex:0] valueForKey:@"gender"];
        NSString token = [[command.arguments objectAtIndex:0] valueForKey:@"token"];
        NSString profileUrl = [[command.arguments objectAtIndex:0] valueForKey:@"profileUrl"];
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

         NSString eventName = [[command.arguments objectAtIndex:0] valueForKey:@"eventName"];
         NSString data = [[command.arguments objectAtIndex:0] valueForKey:@"data"];

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

        NSString eventName = [[command.arguments objectAtIndex:0] valueForKey:@"screenName"];

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
         NSString eventName = [[command.arguments objectAtIndex:0] valueForKey:@"latitude"];
         NSString eventName = [[command.arguments objectAtIndex:0] valueForKey:@"longitude"];


        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
}


@end
