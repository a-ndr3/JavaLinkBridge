﻿using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IO.Swagger.SharpModel
{
    public class Instance
    {   
        public long? Id { get; set; }

        public string Name { get; set; }
        
        public string Type { get; set; }

        public Dictionary<string, object> Properties { get; set; }

        private Instance()
        {

        }

        public static Instance create(Workspace workspace, InstanceType instanceType, string name, Folder folder)
        {
            InstanceControllerApi instanceControllerApi = new InstanceControllerApi();

            var request = new CreateInstanceRequest(instanceType.Id, name);

            return ConvertFromDTO(instanceControllerApi.CreateInstance(request));
        }

        public static Instance create(InstanceType instanceType, string name)
        {
            InstanceControllerApi instanceControllerApi = new InstanceControllerApi();

            var request = new CreateInstanceRequest(instanceType.Id, name);

            return ConvertFromDTO(instanceControllerApi.CreateInstance(request));
        }

        public static Instance create(long id, string name, Dictionary<string, object> props, string type)
        {
            return ConvertFromDTO(new InstanceDTO(id, null, props, name, type));
        }

        public static List<Instance> getInstances(InstanceType instanceType)
        {
            InstanceControllerApi instanceControllerApi = new InstanceControllerApi();

            return instanceControllerApi.GetInstances(instanceType.Id).Select(ConvertFromDTO).ToList();
        
        }

        public static Instance getInstance(long id)
        {
            InstanceControllerApi instanceControllerApi = new InstanceControllerApi();

            return ConvertFromDTO(instanceControllerApi.GetInstance(id));
        }

        public bool delete()
        {
            InstanceControllerApi instanceControllerApi = new InstanceControllerApi();

            var resp = instanceControllerApi.DeleteInstanceWithHttpInfo(Id);

            if (resp.StatusCode == 200)
            {
                UpdateManager.Instance.instances.Remove(Id.Value);
                return true;
            }
            else return false;
        }

        private static Instance ConvertFromDTO(InstanceDTO instanceDTO)
        {
            return new Instance
            {
                Id = instanceDTO.Id,
                Name = instanceDTO.Name,
                Type = instanceDTO.Type,
                Properties = instanceDTO.Properties
            };
        }
    }
}
