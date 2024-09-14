rootProject.name = "cats"
include("apiGateway")
include("apiGateway:service")
include("apiGateway:security")
include("apiGateway:dal")
include("apiGateway:controller")
include("catsMicroservice")
include("catsMicroservice:catsApp")
include("catsMicroservice:catsApp:controller")
include("catsMicroservice:catsApp:service")
include("catsMicroservice:catsApp:dal")
include("catsMicroservice:catsClient")
include("catsMicroservice:catsModel")
include("ownersMicroservice")
include("ownersMicroservice:ownersClient")
include("ownersMicroservice:ownersModel")
include("ownersMicroservice:ownersApp")
include("ownersMicroservice:ownersApp:controller")
include("ownersMicroservice:ownersApp:service")
include("ownersMicroservice:ownersApp:dal")
include("apiGateway:kafka")
findProject(":apiGateway:kafka")?.name = "kafka"
include("apiGateway:kafka")
findProject(":apiGateway:kafka")?.name = "kafka"
include("catsMicroservice:catsApp:kafka")
findProject(":catsMicroservice:catsApp:kafka")?.name = "kafka"
include("ownersMicroservice:ownersApp:kafka")
findProject(":ownersMicroservice:ownersApp:kafka")?.name = "kafka"
include("apiGateway:kafkaMessages")
findProject(":apiGateway:kafkaMessages")?.name = "kafkaMessages"
