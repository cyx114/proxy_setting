# proxy_setting

A Flutter plugin for getting system proxy setting. 

## Getting Started
Sample Code
```dart
import 'package:proxy_setting/proxy_setting.dart';

Map<String, String>? proxy = await ProxySetting.getProxySettings();
String? proxyHost;
String? proxyPort;
if (proxy != null) {
proxyHost = proxy['host'];
proxyPort = proxy['port'];
}
(dio.httpClientAdapter as DefaultHttpClientAdapter).onHttpClientCreate =
  (client) {
if (proxyHost != null && proxyPort != null) {
  client.findProxy = (uri) => "PROXY $proxyHost:$proxyPort;";
}
client.badCertificateCallback =
    (X509Certificate cert, String host, int port) => true;
};
```

