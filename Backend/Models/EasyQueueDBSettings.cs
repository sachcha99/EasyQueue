namespace Backend.Models
{
    public class EasyQueueDBSettings 
    {
        public string ConnectionString { get; set; } = null;
        public string DatabaseName { get; set; } = null;
        public string UserCollectionName { get; set; } = null;
    }
}
