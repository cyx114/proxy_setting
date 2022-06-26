#import "ProxySettingPlugin.h"

@implementation ProxySettingPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"proxy_setting"
            binaryMessenger:[registrar messenger]];
  ProxySettingPlugin* instance = [[ProxySettingPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getProxySettings" isEqualToString:call.method]) {
      CFDictionaryRef originSetting = CFNetworkCopySystemProxySettings();
      NSDictionary *setting = (__bridge_transfer NSDictionary *)originSetting;
    result(setting);
  } else {
    result(FlutterMethodNotImplemented);
  }
}

@end
