using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IO.Swagger.Model
{
    public class BaseDTOJsonConverter : JsonConverter
    {
        public override bool CanConvert(Type objectType)
        {
            return objectType == typeof(BaseDTO) || objectType == typeof(List<BaseDTO>);
        }

        private BaseDTO DeserializeSingleObject(JObject jo, JsonSerializer serializer)
        {
            var type = (string)jo["type"];

            BaseDTO dto;
            switch (type)
            {
                case "BaseDTO":
                    dto = new BaseDTO();
                    break;
                case "InstanceDTO":
                    dto = new InstanceDTO();
                    break;
                case "PropertyDTO":
                    dto = new PropertyDTO();
                    break;
                case "PropertyTypeDTO":
                    dto = new PropertyTypeDTO();
                    break;
                default:
                    throw new Exception($"Unknown type: {type}");
            }

            serializer.Populate(jo.CreateReader(), dto);
            return dto;
        }

        public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
        {
            if (reader.TokenType == JsonToken.StartObject)
            {
                var jo = JObject.Load(reader);
                return DeserializeSingleObject(jo, serializer);
            }
            else if (reader.TokenType == JsonToken.StartArray)
            {
                var ja = JArray.Load(reader);
                var list = new List<BaseDTO>();
                foreach (var item in ja)
                {
                    var jo = (JObject)item;
                    list.Add(DeserializeSingleObject(jo, serializer));
                }
                return list;
            }
            throw new JsonSerializationException("Unexpected token type: " + reader.TokenType);
        }

        public override void WriteJson(JsonWriter writer, object value, JsonSerializer serializer)
        {
            throw new NotImplementedException("Not implemented yet");
        }
    }
}
