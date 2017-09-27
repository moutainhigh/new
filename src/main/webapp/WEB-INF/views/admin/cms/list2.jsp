<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>

<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">${menu.name }</span>
       	</div>
        <div class="actions">
        	<button class="btn sbold blue" id="insertBtn" onclick="post();"><i class="fa fa-plus-circle"></i>增加</button>
            <input id="searchStr" name="searchStr" class="form-control input-inline input-sm" type="text" placeholder="${menu.titleAlias}"/>
			<button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>        	
        </div>
    </div>
    <div class="portlet-body">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th width="10%"> 编号</th>
                    <th> ${menu.titleAlias} </th>
                    <th width="20%"> 日期 </th>
                    <th width="20%"> 操作 </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="entity" varStatus="status">
                    <tr>
                        <td>${entity.cid }</td>
                        <td class="highlight">
                            <div class="warning"></div>&nbsp;
                            <a href="#">${entity.title}</a>
                        </td>
                        <td>${entity.createDate }</td>
                        <td>
                            <button class="btn btn-xs green" onclick="post();"><i class="fa fa-plus-circle"></i> 增加 </button>
                            <button class="btn btn-xs green" onclick="put(${entity.cid});"><i class="fa fa-dot-circle-o"></i> 修改 </button>
                            <button class="btn btn-xs green" onclick="del(${entity.cid},'${entity.title}')"><i class="fa fa-minus-circle"></i> 删除 </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="row">
	    	<div class="col-md-5 col-sm-5">
	    		<label>页数 
	    		<select id="pageSelect" name="pageSelect" class="form-control input-sm input-xsmall input-inline" >
	    			<option value="5">20</option>
	    			<option value="15">50</option>
	    			<option value="20">100</option>	
	    		</select>
	    		</label>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/admin/cms/content/get?mid=${menu.mid }&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->

<script type="text/javascript">

    function search() {
        window.location.href = "<c:url value="/admin/cms/content/get?mid="/>"+ ${menu.mid}+ "&title="+$("#searchStr").val();
    }

    function post() {
        window.location.href = "<c:url value="/admin/cms/content/edit?mid="/>"+ ${menu.mid};
    }

    function put(cid) {
        window.location.href = "<c:url value="/admin/cms/content/edit?cid="/>" + cid + "&mid=${menu.mid}";
    }

    function del(cid,title) {
        var flg = confirm("确定要刪除["+title+"]嗎?");
        if(flg) {
            window.location.href = "<c:url value="/admin/cms/content/delete?cid="/>" + cid + "&mid=${menu.mid}";
        }
    }

    function pageChange(no) {
    	window.location.href = "<c:url value="/admin/cms/content/get?mid="/>"+ ${menu.mid}+"&pageSize="+no+ "&title="+$("#searchStr").val();
    }
    
    $("#pageSelect").change(function(){
    	var size=$(this).children('option:selected').val();//这就是selected的值
    	window.location.href = "<c:url value="/admin/cms/content/get?mid="/>"+ ${menu.mid}+"&pageSize="+size+ "&title="+$("#searchStr").val();
    }) 
</script>