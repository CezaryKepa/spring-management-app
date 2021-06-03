angular.module('app')
.controller('AssetListController', function(AssetService,AssetDeleteService) {
    const vm = this;
    vm.assets = AssetService.getAll();
    function refreshData() {
        vm.assets = AssetService.getAll();
    }
    vm.search = text => {
        vm.assets = AssetService.getAll({text});
    };
    vm.deleteAsset = asset => {
        AssetDeleteService.save(asset.id)
            .then(response => {
                refreshData();
                asset.end = response.data;
            });

    };
});