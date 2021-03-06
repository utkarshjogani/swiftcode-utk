var app = angular.module('chatApp', ['ngMaterial']);
app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('blue')
        .accentPalette('blue');
});

app.controller('chatController', function ($scope, $sce) {


    var exampleSocket = new WebSocket('wss://swiftcode-newsbot-utk.herokuapp.com/chatSocket');
    exampleSocket.onmessage = function (event) {

        var jsonData = JSON.parse(event.data);
        jsonData.time = new Date()
            .toLocaleTimeString();
        $scope.messages.push(jsonData);
        $scope.$apply();
        console.log(jsonData);

    };
    $scope.sendMessage = function () {

        exampleSocket.send($scope.userMessage);
        $scope.userMessage = '';
    };
    $scope.trust = $sce.trustAsHtml;
    $scope.messages = [







];






});