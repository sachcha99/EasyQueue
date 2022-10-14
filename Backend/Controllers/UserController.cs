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
    }
}
