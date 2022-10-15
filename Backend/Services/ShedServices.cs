using Microsoft.Extensions.Options;
using MongoDB.Driver;
using Backend.Models;

namespace Backend.Services
{
    public class ShedServices
    {
        private readonly IMongoCollection<Shed> _shedCollection;

        public ShedServices(IOptions<EasyQueueDBSettings> shedServices)
        {
            var mongoClient = new MongoClient(shedServices.Value.ConnectionString);
            var mongoDatabase = mongoClient.GetDatabase(shedServices.Value.DatabaseName);

            _shedCollection = mongoDatabase.GetCollection<Shed>("Shed");

        }

        public async Task<List<Shed>> GetAsync() =>
            await _shedCollection.Find(x => true).ToListAsync();
        public async Task<Shed?> GetAsync(string id) =>
            await _shedCollection.Find(x => x.Id == id).FirstOrDefaultAsync();
        public async Task CreateAsync(Shed shed) =>
            await _shedCollection.InsertOneAsync(shed);
        public async Task UpdateAsync(string id, Shed shed) =>
            await _shedCollection.ReplaceOneAsync(x => x.Id == id, shed);
        public async Task RemoveAsync(string id) =>
            await _shedCollection.DeleteOneAsync(x => x.Id == id);
        
    }
}
