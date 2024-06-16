using IO.Swagger.Api;
using IO.Swagger.Model;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime;
using System.Text;
using System.Threading.Tasks;

namespace IO.Swagger.SharpModel
{
    public class UpdateManager
    {
        private static readonly UpdateManager updateManager = new UpdateManager();

        private GeneralControllerApi generalControllerApi;

        public Dictionary<long, Instance> instances;

        private JsonSerializerSettings jsonSerializerSettings;

        static UpdateManager()
        {
            updateManager.generalControllerApi = new GeneralControllerApi();
            updateManager.instances = new Dictionary<long, Instance>();

            updateManager.jsonSerializerSettings = new JsonSerializerSettings
            {
                Converters = new List<JsonConverter> { new BaseDTOJsonConverter() },
                TypeNameHandling = TypeNameHandling.Auto
            };
        }
        private UpdateManager()
        {

        }

        public static UpdateManager Instance
        {
            get
            {
                return updateManager;
            }
        }

        public void conclude()
        {
            List<BaseDTO> updates;

            //each element from the list into json string
            var result = "[" + string.Join(",", generalControllerApi.Conclude()) + "]";

            if (IsJsonArray(result))
            {
                updates = JsonConvert.DeserializeObject<List<BaseDTO>>(result, jsonSerializerSettings);
            }
            else
            {
                var singleUpdate = JsonConvert.DeserializeObject<BaseDTO>(result, jsonSerializerSettings);
                updates = new List<BaseDTO> { singleUpdate };
            }

            updateManager.ApplyUpdates(updates);
        }

        public void getInitialServerUpdates()
        {
            List<BaseDTO> updates;

            var updatesFromServer = generalControllerApi.GetInitialConnectUpdatesFromServer();

            if (updatesFromServer == null)
                return;

            var result = "[" + string.Join(",", updatesFromServer) + "]";

            if (IsJsonArray(result))
            {
                updates = JsonConvert.DeserializeObject<List<BaseDTO>>(result, jsonSerializerSettings);
            }
            else
            {
                var singleUpdate = JsonConvert.DeserializeObject<BaseDTO>(result, jsonSerializerSettings);
                updates = new List<BaseDTO> { singleUpdate };
            }

            updateManager.ApplyUpdates(updates);
        }

        public void getUpdates()
        {
            List<BaseDTO> updates;

            var updatesFromServer = generalControllerApi.CheckUpdates();

            if (updatesFromServer == null)
                return;

            var result = "[" + string.Join(",", updatesFromServer) + "]";

            if (IsJsonArray(result))
            {
                updates = JsonConvert.DeserializeObject<List<BaseDTO>>(result, jsonSerializerSettings);
            }
            else
            {
                var singleUpdate = JsonConvert.DeserializeObject<BaseDTO>(result, jsonSerializerSettings);
                updates = new List<BaseDTO> { singleUpdate };
            }

            updateManager.ApplyUpdates(updates);
        }

        public bool IsJsonArray(string json)
        {
            json = json.Trim();
            return json.StartsWith("[") && json.EndsWith("]");
        }

        private void ApplyUpdates(List<BaseDTO> updates)
        {
            foreach (var dto in updates)
            {
                switch (dto)
                {
                    case InstanceDTO instanceDTO:
                        UpdateOrCreateInstance(instanceDTO);
                        break;
                    case PropertyDTO propertyDTO:
                        // ProcessProperty(propertyDTO); //TODO add when test work with properties
                        break;
                    case PropertyTypeDTO propertyTypeDTO:
                        //HandlePropertyType(propertyTypeDTO); //TODO add when test work with properties
                        break;
                    default:
                        Console.WriteLine("Unknown DTO type.");
                        break;
                }
            }
        }

        private void UpdateOrCreateInstance(InstanceDTO dto)
        {
            Instance instance = FindInstanceByOldOrNewId(dto.OldId, dto.Id);

            if (instance == null)
            {
                if (dto.Id.HasValue)
                {
                    instance = SharpModel.Instance.create(dto.Id.Value, dto.Type, dto.Name);
                }
            }
            else
            {
                if (dto.Id.HasValue && dto.OldId.HasValue && dto.OldId.Value != dto.Id.Value)
                {
                    instances.Remove(instance.Id.Value);
                    instance.Id = dto.Id.Value;
                }
            }

            if (!string.IsNullOrEmpty(dto.Name))
                instance.Name = dto.Name;

            instances.Add(instance.Id.Value,instance);

            if (instance.Id < 0)
            {
                Console.WriteLine("Instance with negative id: " + instance.Id);
            }
        }

        private Instance FindInstanceByOldOrNewId(long? oldId, long? newId)
        {
            Instance instance = null;

            if (oldId.HasValue && instances.TryGetValue(oldId.Value, out instance))
                return instance;

            if (newId.HasValue && instances.TryGetValue(newId.Value, out instance))
                return instance;

            return null;
        }

    }
}
