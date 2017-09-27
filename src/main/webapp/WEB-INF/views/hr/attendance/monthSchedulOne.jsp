<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/fullcalendar-3.3/fullcalendar.min.css" />"/>

<div class="portlet light calendar">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">排班列表</span>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <div id="calendar" class="has-toolbar"> </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/moment.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/fullcalendar-3.3/fullcalendar.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/fullcalendar-3.3/zh-cn.js" />"></script>

<script>
    var AppCalendar = function () {
        return {
            init: function () {
                this.initCalendar()
            }, initCalendar: function () {
                if (jQuery().fullCalendar) {
                    var now = new Date,
                        date = now.getDate(),
                        month = now.getMonth(),
                        year = now.getFullYear(),
                        r = {};
                   $("#calendar").fullCalendar({
                        header: r,
                        defaultView: "month",
                        events: [{
                            title: "All Day Event",
                            start: new Date(year, month, 1),
                            backgroundColor: App.getBrandColor("yellow")
                        }, {
                            title: "Long Event",
                            start: new Date(year, month, date - 5),
                            end: new Date(year, month, date - 2),
                            backgroundColor: App.getBrandColor("green")
                        }, {
                            title: "Repeating Event",
                            start: new Date(year, month, date - 3, 16, 0),
                            allDay: !1,
                            backgroundColor: App.getBrandColor("red")
                        }, {
                            title: "Repeating Event",
                            start: new Date(year, month, date + 4, 16, 0),
                            allDay: !1,
                            backgroundColor: App.getBrandColor("green")
                        }, {
                            title: "Meeting",
                            start: new Date(year, month, date, 10, 30),
                            allDay: !1
                        }, {
                            title: "Lunch",
                            start: new Date(year, month, date, 12, 0),
                            end: new Date(year, month, date, 14, 0),
                            backgroundColor: App.getBrandColor("grey"),
                            allDay: !1
                        }, {
                            title: "Birthday Party",
                            start: new Date(year, month, date + 1, 19, 0),
                            end: new Date(year, month, date + 1, 22, 30),
                            backgroundColor: App.getBrandColor("purple"),
                            allDay: !1
                        }, {
                            title: "Click for Google",
                            start: new Date(year, month, 28),
                            end: new Date(year, month, 29),
                            backgroundColor: App.getBrandColor("yellow"),
                            url: "http://google.com/"
                        }]
                    })
                }
            }
        }
    }();
    jQuery(document).ready(function () {
        AppCalendar.init()
    });

</script>