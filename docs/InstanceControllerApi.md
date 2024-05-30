# IO.Swagger.Api.InstanceControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**CreateInstance**](InstanceControllerApi.md#createinstance) | **POST** /api/instance/create | 
[**DeleteInstance**](InstanceControllerApi.md#deleteinstance) | **DELETE** /api/instance/delete/{id} | 
[**GetInstance**](InstanceControllerApi.md#getinstance) | **GET** /api/instance/get/{id} | 
[**GetInstances**](InstanceControllerApi.md#getinstances) | **GET** /api/instance/getInstances/{typeId} | 

<a name="createinstance"></a>
# **CreateInstance**
> InstanceDTO CreateInstance (CreateInstanceRequest body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class CreateInstanceExample
    {
        public void main()
        {
            var apiInstance = new InstanceControllerApi();
            var body = new CreateInstanceRequest(); // CreateInstanceRequest | 

            try
            {
                InstanceDTO result = apiInstance.CreateInstance(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling InstanceControllerApi.CreateInstance: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateInstanceRequest**](CreateInstanceRequest.md)|  | 

### Return type

[**InstanceDTO**](InstanceDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="deleteinstance"></a>
# **DeleteInstance**
> void DeleteInstance (long? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteInstanceExample
    {
        public void main()
        {
            var apiInstance = new InstanceControllerApi();
            var id = 789;  // long? | 

            try
            {
                apiInstance.DeleteInstance(id);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling InstanceControllerApi.DeleteInstance: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **long?**|  | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getinstance"></a>
# **GetInstance**
> InstanceDTO GetInstance (long? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetInstanceExample
    {
        public void main()
        {
            var apiInstance = new InstanceControllerApi();
            var id = 789;  // long? | 

            try
            {
                InstanceDTO result = apiInstance.GetInstance(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling InstanceControllerApi.GetInstance: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **long?**|  | 

### Return type

[**InstanceDTO**](InstanceDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getinstances"></a>
# **GetInstances**
> List<InstanceDTO> GetInstances (long? typeId)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetInstancesExample
    {
        public void main()
        {
            var apiInstance = new InstanceControllerApi();
            var typeId = 789;  // long? | 

            try
            {
                List&lt;InstanceDTO&gt; result = apiInstance.GetInstances(typeId);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling InstanceControllerApi.GetInstances: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **typeId** | **long?**|  | 

### Return type

[**List<InstanceDTO>**](InstanceDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
