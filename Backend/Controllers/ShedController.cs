using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Backend.Models;
using Backend.Services;

namespace Backend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ShedController : ControllerBase
    {
        private readonly ShedServices _shedServices;

        public ShedController(ShedServices shedServices)
        {
            _shedServices = shedServices;
        }

        [HttpGet]
        public async Task<List<Shed>> GetShed()
            => await _shedServices.GetAsync();

        [HttpPost]
        public async Task<Shed> PostShed(Shed shed)
        {
            await _shedServices.CreateAsync(shed);

            return shed;
        }

        [HttpGet("{id}")]
        public async Task<Shed> GetShed(string id)
            => await _shedServices.GetAsync(id);
        
        [HttpPut("{id}")]
        public async Task<Shed> PutShed(string id, Shed shed)
        {
            await _shedServices.UpdateAsync(id, shed);

            return shed;
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteShed(string id)
        {
            await _shedServices.RemoveAsync(id);

            return NoContent();
        }
    }
}