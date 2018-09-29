## soapui-global-headers
SoapUI plugin to enable global HTTP headers, that will be added to all projects in the soapUI workspace.

Possible use case is to add auth headers, client identification (token), logging etc.

### Usage 
- download latest release from github releases, add to your local SoapUI installation:
  - copy `request-listeners.xml` to `%SOAPUI_HOME%/bin/listeners/` 
  - copy `soapui-header-filter-X.jar` to `%SOAPUI_HOME%/bin/ext/`
  - start SoapUI
  - add global property in Preferences -> Global properties. Name needs to start with
  prefix `header-`, example: `header-X-Token`, will add `X-Token` HTTP header 
- it is tested as working for both SOAP & REST projects.

### Compatibility
- should work with any SoapUI 5.x

### Implementation
- implemented soapUI interface `com.eviware.soapui.impl.wsdl.submit.RequestFilter`
- maven project added