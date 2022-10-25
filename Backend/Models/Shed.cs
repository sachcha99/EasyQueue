using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Backend.Models
{
    public class Shed
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string? Id { get; set; }

        [BsonElement("Name")]
        public string Name { get; set; } = null;

        [BsonElement("Address")]
        public string Address { get; set; } = null;

        [BsonElement("Status")]
        public string Status { get; set; } = null;

        [BsonElement("QueueStartTime")]
        public string QueueStartTime { get; set; } = null;

        [BsonElement("QueueEndTime")]
        public string QueueEndTime { get; set; } = null;


    }
}
