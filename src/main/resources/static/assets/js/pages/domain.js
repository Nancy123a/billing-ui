$(function() {

    moment.tz.setDefault("UTC");

    $('#daterange').daterangepicker({
        startDate: moment().startOf('day'),
        endDate: moment().endOf('day'),
        minDate: '01/01/2012',
        maxDate: moment().hour(23),
    //                showDropdowns: false,
    //                timePicker: true,
    //                timePickerIncrement: 60,
    //                timePicker24Hour: true,
        locale: {
            format: 'DD/MM/YYYY HH:00'
        },
        ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    });

    $.fn.select2.defaults.set("placeholder", {id: 'none', text: 'None'});

    $('#customer,#vendor').select2({
        dataAdapter: $.fn.select2.amd.require('select2/data/extended-ajax'),
        defaultResults: [{id: 0, text: 'All'}],
        multiple: true,
        minimumInputLength: 3,
        language: {
            inputTooShort: function () {
                return "Enter 3 characters";
            }
        },
        ajax : {
            url : "/api/carriers",
            quietMillis : 500,
            data : function(params) {
                return {
                    carrierName: params.term,
                    page: (params.page || 1) - 1,
                    projection: 'select2'
                }
            },
            processResults : function (data,page) {
                if (data._embedded)
                    return { results: data._embedded.carriers };
                else
                    return {results: data};
            }
        },
        theme: 'bootstrap'
    });

    $('#icountry,#ecountry').select2({
        dataAdapter: $.fn.select2.amd.require('select2/data/extended-ajax'),
        defaultResults: [{id: 0, text: 'All'}],
        multiple: true,
        minimumInputLength: 3,
        language: {
            inputTooShort: function () {
                return "Enter 3 characters";
            }
        },
        ajax : {
            url : "/api/countries",
            quietMillis : 500,
            data : function(params) {
                return {
                    countryName: params.term,
                    page: (params.page || 1) - 1,
                    projection: 'select2'
                }
            },
            processResults : function (data,page) {
                if (data._embedded)
                    return { results: data._embedded.countries };
                else
                    return {results: data};
            }
        },
        theme: 'bootstrap'
    });

    $('#ioperator,#eoperator').select2({
        dataAdapter: $.fn.select2.amd.require('select2/data/extended-ajax'),
        defaultResults: [{id: 0, text: 'All'}],
        multiple: true,
        minimumInputLength: 3,
        language: {
            inputTooShort: function () {
                return "Enter 3 characters";
            }
        },
        ajax : {
            url : "/api/operators",
            quietMillis : 500,
            data : function(params) {
                return {
                    operatorName: params.term,
                    page: (params.page || 1) - 1,
                    projection: 'select2'
                }
            },
            processResults : function (data,page) {
                if (data._embedded)
                    return { results: data._embedded.operators };
                else
                    return { results: data };
            }
        },
        theme: 'bootstrap'
    });
});
