angular.module('app')
.controller('UserListController', function(UserService,UserDeleteService) {
    const vm = this;
    vm.users = UserService.getAll();
    function refreshData() {
        vm.users = UserService.getAll();
    }
    vm.search = lastName => {
        vm.users = UserService.getAll({lastName});
    };
    vm.deleteUser = user => {
        UserDeleteService.save(user.id)
            .then(response => {
                refreshData();
               user.end = response.data;
            });

    };
});