using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Backend.Models
{
    public class User
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string? Id { get; set; }

        [BsonElement("Name")]
        public string Name { get; set; } = null;

        [BsonElement("Address")]
        public string Address { get; set; } = null;

        [BsonElement("Mobile")]
        public string Mobile { get; set; } = null;

        [BsonElement("Email")]
        public string Email { get; set; } = null;
    }
}
