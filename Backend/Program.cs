using Backend.Models;
using Backend.Services;
using System.Net;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddHttpClient();

// Add services to the container.
builder.Services.Configure<EasyQueueDBSettings>
    (builder.Configuration.GetSection("DevNetStoreDatabase"));

builder.Services.AddSingleton<UserServices>();
builder.Services.AddSingleton<ShedServices>();

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddHttpClient( "EasyQueueAPI", client =>
{
    client.BaseAddress = new Uri("https://192.168.1.104:7249");
    client.DefaultRequestHeaders.Add("Accept", "application/json");
});


var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseCors(policy => policy.AllowAnyHeader().AllowAnyMethod()
                            .SetIsOriginAllowed(_ => true).AllowCredentials());

// app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
