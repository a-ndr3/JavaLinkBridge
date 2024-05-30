# IO.Swagger.Api.PropertyControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**GetProperties**](PropertyControllerApi.md#getproperties) | **GET** /api/property/getProperties/{instanceId} | 
[**SetProperty**](PropertyControllerApi.md#setproperty) | **POST** /api/property/{id}/set | 

<a name="getproperties"></a>
# **GetProperties**
> List<PropertyDTO> GetProperties (long? instanceId)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetPropertiesExample
    {
        public void main()
        {
            var apiInstance = new PropertyControllerApi();
            var instanceId = 789;  // long? | 

            try
            {
                List&lt;PropertyDTO&gt; result = apiInstance.GetProperties(instanceId);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling PropertyControllerApi.GetProperties: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **instanceId** | **long?**|  | 

### Return type

[**List<PropertyDTO>**](PropertyDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="setproperty"></a>
# **SetProperty**
> void SetProperty (SetPropertyRequest body, long? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class SetPropertyExample
    {
        public void main()
        {
            var apiInstance = new PropertyControllerApi();
            var body = new SetPropertyRequest(); // SetPropertyRequest | 
            var id = 789;  // long? | 

            try
            {
                apiInstance.SetProperty(body, id);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling PropertyControllerApi.SetProperty: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SetPropertyRequest**](SetPropertyRequest.md)|  | 
 **id** | **long?**|  | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
