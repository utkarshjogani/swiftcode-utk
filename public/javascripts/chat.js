var app = angular.module('chatApp', ['ngMaterial']);

app.controller('chatController', function ($scope) {

    $scope.messages = [
        {
            'sender': 'USER',
            'text': 'HELLO'
	},
        {
            'sender': 'BOT',
            'text': 'Hi, what can i do for you?'
	},
        {
            'sender': 'USER',
            'text': 'HELLO'
	}
   ];
});