using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Backend.Models;
using Backend.Services;

namespace Backend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly UserServices _userServices;

        public UserController(UserServices userServices)
        {
            _userServices = userServices;
        }

        [HttpGet]
        public async Task<List<User>> GetUser()
            => await _userServices.GetAsync();

        [HttpPost]
        public async Task<User> PostUser(User user)
        {
            await _userServices.CreateAsync(user);

            return user;
        }

        [HttpGet("{id}")]
        public async Task<User> GetUser(string id)
            => await _userServices.GetAsync(id);
        
        [HttpPut("{id}")]
        public async Task<User> PutUser(string id, User user)
        {
            await _userServices.UpdateAsync(id, user);

            return user;
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUser(string id)
        {
            await _userServices.RemoveAsync(id);

            return NoContent();
        }
     
    }
}
