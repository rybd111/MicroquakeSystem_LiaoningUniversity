// 工具条文件头说明.
// ("文件类型字符串" "资源文件名" "透明颜色" "位置,可以是：top,left,bottom,right" "是否可以浮动，Y \N ")
("MxDrawToolBarFile" "MxDraw.dll" (214 211 206) "top" "常用工具" "Y" "" "15")

// 文件中的工具命令字符串每项说明.
// ("命令名称" "命令提示字符串" "命令" "图标索引字符串" "ID类型" "资源文件名" "命令ID" "没有命定义时,是否不加载工具按钮")

// 资源文名件可以是本地，或网上的bmp文件
// 如下，是从工具条所在本地目录的bmp加载,local:表示是个本地的bmp
//("自定义打文件按钮" "自定义打文件按钮" "Mx_OpenMxg" "" "" "local:mytoolbar.bmp")

// 如下，是从工具条所在网络目录的bmp加载,web:表示是网上加载
//("自定义打文件按钮" "自定义打文件按钮" "Mx_OpenMxg" "" "" "web:mytoolbar.bmp")

("新建" "新建" "Mx_New" "IDB_NEW_BITMAP" "" "MxEdit.mrx" "" "Y")
("打开mxg文件" "打开mxg文件" "Mx_OpenMxg" "IDB_OPENMXG_BITMAP")
("打开dwg文件" "打开dwg文件" "FastOpenDwg" "IDB_OPENDWG_BITMAP")
//("打开网上dwg文件" "打开网上dwg文件" "_OpenWebDwg" "IDB_OPENWEBDWG_BITMAP" "" "" "" "Y")
//("保存dwg文件" "保存dwg文件" "SaveDwg" "IDB_SAVEDWG_BITMAP"  "" "MxDraw.dll" "15")
("保存" "保存文件" "SaveDwg" "IDB_SAVEDWG_BITMAP")
("保存为mxg文件" "保存为mxg文件" "Mx_SaveAsMxg" "IDB_SAVEMXG_BITMAP")
("另存为dwg文件" "另存为dwg文件" "SaveAsDwg" "IDB_SAVEASDWG_BITMAP")
("SEPARATOR")
("缩放"     "视区缩放命令" "ZoomR" "IDB_ZOOM_BITMAP")
("窗口缩放" "窗口缩放命令" "WindowZoom" "IDB_ZOOMW_BITMAP")
("范围缩放" "范围缩放命令" "ZoomE" "IDB_ZOOME_BITMAP")
("视区移动" "视区移动命令" "P" "IDB_PAN_BITMAP")
("用户坐标系" "UCS命令" "Mx_Ucs" "IDB_UCS_BITMAP"  "" "MxEdit.mrx" "" "Y")
("视区旋转" "PLAN命令" "Mx_Plan" "IDB_PLAN_BITMAP"  "" "MxEdit.mrx" "" "Y")

("打开所有图层" "打开所有图层命令" "_OpenAllLayer" "IDB_ALLOPENLAYER_BITMAP")
("关闭选择实体的图层" "关闭选择实体的图层命令" "_SelOffLayer" "IDB_SELOFFLAYER_BITMAP")


("SEPARATOR")
("前一个视区" "缩放上一个" "ZoomP" "ID_PRVVIEW_BITMAP")
("放大镜" "放大镜命令" "MagnifyingGlass" "IDB_MAGNIFYINGCLASS_BITMAP")
("鸟瞰" "鸟瞰命令" "DsViewer" "IDB_DSVIEWER_BITMAP")

("重新生成图形" "重新生成图形" "Re" "IDB_REGEN_BITMAP")

("绘图模式" "绘图模式" "_DrawModle" "IDB_DRAWMODLE_BITMAP")
("视区背景色" "视区背景色" "_ViewColor" "IDB_VIEWCOLOR_BITMAP")
("线重显示" "线重模式" "_ShowLineWeight" "IDB_LINEWEIGHT_BITMAP")
("全屏显示" "全屏模式" "MxFullScreen" "IDB_FULLSCREEN_BITMAP")
("SEPARATOR")
("对象特性" "对象特性" "Mx_Properties" "IDB_PROPERTY_BITMAP" "" "PropertyEditor.mrx" "" "Y")
("快速选择" "快速选择" "Mx_QuickSelect" "IDB_QUICK_SELECT" "" "MxEdit.mrx" "" "Y")
("特性匹配" "特性匹配" "Mx_FeatureMatch" "IDB_FEATUREMATCH_BITMAP" "" "MxCADTools.mrx" "" "Y")

("SEPARATOR")
("回退" "回退" "U" "IDB_UNDO_BITMAP" "ID_UNDO_BUTTOM")
("重作" "重作" "Redo" "IDB_REDO_BITMAP" "ID_REDO_BUTTOM")
("SEPARATOR")
("打印图形" "打印图形" "Plot" "IDB_PRINT_BITMAP")

//("SEPARATOR")
("DWF导出" "导出Dwf文件" "ExportDwf" "IDB_EXPORTDWF_BITMAP")
("DWF导入" "导入Dwf文件" "ImportDwf" "IDB_IMPORTDWF_BITMAP")

("SEPARATOR")
("PDF导出" "导出PDF文件" "ExportPdf" "IDB_IMPORTPDF_BITMAP")
("保存为JPG" "保存为JPG" "_ExportJpg" "IDB_IMPORTJPG_BITMAP")


