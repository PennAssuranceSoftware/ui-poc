'use strict';

MetronicApp.controller('UserProfileController', function($rootScope, $scope, $http, $timeout, $state, $stateParams) {
    $scope.$on('$viewContentLoaded', function() {   
        Metronic.initAjax(); // initialize core components
        Layout.setSidebarMenuActiveLink('set', $('#sidebar_menu_link_profile')); // set profile link active in sidebar menu

        $state.current.data = {pageTitle: 'Policy ' + $stateParams.policyNumber, pageSubTitle: ''};
        // $state.go('policy.dashboard'); 

        // $rootScope.settings.layout.pageSidebarClosed = true; 
    });
}); 
