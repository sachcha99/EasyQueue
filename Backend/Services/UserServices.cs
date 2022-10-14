using Microsoft.Extensions.Options;
using MongoDB.Driver;
using Backend.Models;

namespace Backend.Services
{
    public class UserServices
    {
        private readonly IMongoCollection<User> _userCollection;

        public UserServices(IOptions<EasyQueueDBSettings> userServices)
        {
            var mongoClient = new MongoClient(userServices.Value.ConnectionString);
            var mongoDatabase = mongoClient.GetDatabase(userServices.Value.DatabaseName);

            _userCollection = mongoDatabase.GetCollection<User>
                (userServices.Value.UserCollectionName);

        }

        public async Task<List<User>> GetAsync() =>
            await _userCollection.Find(x => true).ToListAsync();
        public async Task<User> GetAsync(string id) =>
           await _userCollection.Find(x => x.Id == id).FirstOrDefaultAsync();
        public async Task CreateAsync(User user) =>
            await _userCollection.InsertOneAsync(user);
        public async Task UpdateAsync(string id, User user) =>
           await _userCollection.ReplaceOneAsync(x => x.Id == id, user);
        public async Task RemoveAsync(string id) =>
            await _userCollection.DeleteOneAsync(x => x.Id == id);
    }
}
