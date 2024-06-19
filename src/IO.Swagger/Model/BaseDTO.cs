/* 
 * OpenAPI definition
 *
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0
 * 
 * Generated by: https://github.com/swagger-api/swagger-codegen.git
 */
using System;
using System.Linq;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Runtime.Serialization;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using JsonSubTypes;
using System.ComponentModel.DataAnnotations;
using SwaggerDateConverter = IO.Swagger.Client.SwaggerDateConverter;
namespace IO.Swagger.Model
{
    /// <summary>
    /// BaseDTO
    /// </summary>
    [DataContract]
    [JsonConverter(typeof(JsonSubtypes), "type")]
    [JsonSubtypes.KnownSubType(typeof(InstanceDTO), "InstanceDTO")]
    [JsonSubtypes.KnownSubType(typeof(ElementDTO), "ElementDTO")]
    [JsonSubtypes.KnownSubType(typeof(WorkspaceDTO), "WorkspaceDTO")]
    [JsonSubtypes.KnownSubType(typeof(InstanceTypeDTO), "InstanceTypeDTO")]
    [JsonSubtypes.KnownSubType(typeof(PropertyTypeDTO), "PropertyTypeDTO")]
    [JsonSubtypes.KnownSubType(typeof(PropertyDTO), "PropertyDTO")]
    public partial class BaseDTO : IEquatable<BaseDTO>, IValidatableObject
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="BaseDTO" /> class.
        /// </summary>
        /// <param name="id">id.</param>
        /// <param name="oldId">oldId.</param>
        /// <param name="properties">properties.</param>
        /// <param name="name">name.</param>
        /// <param name="type">type (required).</param>
        public BaseDTO(string type = default(string), long? id = default(long?), long? oldId = default(long?), Dictionary<string, Object> properties = default(Dictionary<string, Object>), string name = default(string))
        {
            if (id == null)
            {

            }
            else
            {// to ensure "type" is required (not null)
                if (type == null)
                {
                    throw new InvalidDataException("type is a required property for BaseDTO and cannot be null");
                }
                else
                {
                    this.Type = type;
                }
                this.Id = id;
                this.OldId = oldId;
                this.Properties = properties;
                this.Name = name;
            }
        }

        /// <summary>
        /// Gets or Sets Id
        /// </summary>
        [DataMember(Name = "id", EmitDefaultValue = false)]
        public long? Id { get; set; }

        /// <summary>
        /// Gets or Sets OldId
        /// </summary>
        [DataMember(Name = "oldId", EmitDefaultValue = false)]
        public long? OldId { get; set; }

        /// <summary>
        /// Gets or Sets Properties
        /// </summary>
        [DataMember(Name = "properties", EmitDefaultValue = false)]
        public Dictionary<string, Object> Properties { get; set; }

        /// <summary>
        /// Gets or Sets Name
        /// </summary>
        [DataMember(Name = "name", EmitDefaultValue = false)]
        public string Name { get; set; }

        /// <summary>
        /// Gets or Sets Type
        /// </summary>
        [DataMember(Name = "type", EmitDefaultValue = false)]
        public string Type { get; set; }

        /// <summary>
        /// Returns the string presentation of the object
        /// </summary>
        /// <returns>String presentation of the object</returns>
        public override string ToString()
        {
            var sb = new StringBuilder();
            sb.Append("class BaseDTO {\n");
            sb.Append("  Id: ").Append(Id).Append("\n");
            sb.Append("  OldId: ").Append(OldId).Append("\n");
            sb.Append("  Properties: ").Append(Properties).Append("\n");
            sb.Append("  Name: ").Append(Name).Append("\n");
            sb.Append("  Type: ").Append(Type).Append("\n");
            sb.Append("}\n");
            return sb.ToString();
        }

        /// <summary>
        /// Returns the JSON string presentation of the object
        /// </summary>
        /// <returns>JSON string presentation of the object</returns>
        public virtual string ToJson()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }

        /// <summary>
        /// Returns true if objects are equal
        /// </summary>
        /// <param name="input">Object to be compared</param>
        /// <returns>Boolean</returns>
        public override bool Equals(object input)
        {
            return this.Equals(input as BaseDTO);
        }

        /// <summary>
        /// Returns true if BaseDTO instances are equal
        /// </summary>
        /// <param name="input">Instance of BaseDTO to be compared</param>
        /// <returns>Boolean</returns>
        public bool Equals(BaseDTO input)
        {
            if (input == null)
                return false;

            return
                (
                    this.Id == input.Id ||
                    (this.Id != null &&
                    this.Id.Equals(input.Id))
                ) &&
                (
                    this.OldId == input.OldId ||
                    (this.OldId != null &&
                    this.OldId.Equals(input.OldId))
                ) &&
                (
                    this.Properties == input.Properties ||
                    this.Properties != null &&
                    input.Properties != null &&
                    this.Properties.SequenceEqual(input.Properties)
                ) &&
                (
                    this.Name == input.Name ||
                    (this.Name != null &&
                    this.Name.Equals(input.Name))
                ) &&
                (
                    this.Type == input.Type ||
                    (this.Type != null &&
                    this.Type.Equals(input.Type))
                );
        }

        /// <summary>
        /// Gets the hash code
        /// </summary>
        /// <returns>Hash code</returns>
        public override int GetHashCode()
        {
            unchecked // Overflow is fine, just wrap
            {
                int hashCode = 41;
                if (this.Id != null)
                    hashCode = hashCode * 59 + this.Id.GetHashCode();
                if (this.OldId != null)
                    hashCode = hashCode * 59 + this.OldId.GetHashCode();
                if (this.Properties != null)
                    hashCode = hashCode * 59 + this.Properties.GetHashCode();
                if (this.Name != null)
                    hashCode = hashCode * 59 + this.Name.GetHashCode();
                if (this.Type != null)
                    hashCode = hashCode * 59 + this.Type.GetHashCode();
                return hashCode;
            }
        }

        /// <summary>
        /// To validate all properties of the instance
        /// </summary>
        /// <param name="validationContext">Validation context</param>
        /// <returns>Validation Result</returns>
        IEnumerable<System.ComponentModel.DataAnnotations.ValidationResult> IValidatableObject.Validate(ValidationContext validationContext)
        {
            return this.BaseValidate(validationContext);
        }

        /// <summary>
        /// To validate all properties of the instance
        /// </summary>
        /// <param name="validationContext">Validation context</param>
        /// <returns>Validation Result</returns>
        protected IEnumerable<System.ComponentModel.DataAnnotations.ValidationResult> BaseValidate(ValidationContext validationContext)
        {
            yield break;
        }
    }
}
