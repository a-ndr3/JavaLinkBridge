# IO.Swagger.Api.InstanceTypeControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**CreateInstanceType**](InstanceTypeControllerApi.md#createinstancetype) | **POST** /api/instanceType/create | 
[**DeleteInstanceType**](InstanceTypeControllerApi.md#deleteinstancetype) | **DELETE** /api/instanceType/delete/{id} | 
[**GetInstanceTypes**](InstanceTypeControllerApi.md#getinstancetypes) | **GET** /api/instanceType/getTypes | 

<a name="createinstancetype"></a>
# **CreateInstanceType**
> InstanceTypeDTO CreateInstanceType (CreateInstanceTypeRequest body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class CreateInstanceTypeExample
    {
        public void main()
        {
            var apiInstance = new InstanceTypeControllerApi();
            var body = new CreateInstanceTypeRequest(); // CreateInstanceTypeRequest | 

            try
            {
                InstanceTypeDTO result = apiInstance.CreateInstanceType(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling InstanceTypeControllerApi.CreateInstanceType: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateInstanceTypeRequest**](CreateInstanceTypeRequest.md)|  | 

### Return type

[**InstanceTypeDTO**](InstanceTypeDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="deleteinstancetype"></a>
# **DeleteInstanceType**
> void DeleteInstanceType (long? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteInstanceTypeExample
    {
        public void main()
        {
            var apiInstance = new InstanceTypeControllerApi();
            var id = 789;  // long? | 

            try
            {
                apiInstance.DeleteInstanceType(id);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling InstanceTypeControllerApi.DeleteInstanceType: " + e.Message );
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
<a name="getinstancetypes"></a>
# **GetInstanceTypes**
> List<InstanceTypeDTO> GetInstanceTypes ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetInstanceTypesExample
    {
        public void main()
        {
            var apiInstance = new InstanceTypeControllerApi();

            try
            {
                List&lt;InstanceTypeDTO&gt; result = apiInstance.GetInstanceTypes();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling InstanceTypeControllerApi.GetInstanceTypes: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List<InstanceTypeDTO>**](InstanceTypeDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
