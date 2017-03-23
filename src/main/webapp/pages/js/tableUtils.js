/*
 * 
 * <!-- DataTables -->
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css"/>
		<script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
		<script src="https://cdn.datatables.net/1.10.13/js/dataTables.bootstrap.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.13/sorting/datetime-moment.js"></script>
 */

function createTable( data, parentContainerId, columns, columnNames, onClickCallback ){
     var table = document.createElement( "table" );
     table.className = "table table-striped table-bordered";
     table.cellspacing = "0";
     table.width = "100%";
     var header = document.createElement( "thead" );
     table.appendChild( header );
     var tr = document.createElement( "tr" );
     header.appendChild( tr );
     for( var i = 0 ; i < columnNames.length ; i++ ){
         var th = document.createElement( "th" );
         th.innerHTML = columnNames[i];
         tr.appendChild( th );
     }

     var body = document.createElement( "tbody" );
     for( var i = 0 ; i < data.length ; i++ ){
        tr = document.createElement( "tr" );
        if(onClickCallback){
        	tr.style = "cursor: pointer;"
        }
        for( var j = 0; j < columns.length ; j++ ){
            var td = document.createElement( "td" );
            td.innerHTML = data[ i ][ columns[j] ];
            tr.appendChild( td );
        }
    	 body.appendChild( tr );
     }
     table.appendChild( body );
     
     var parent = document.getElementById( parentContainerId );
     parent.appendChild( table );

     //Automatically detect argentinian date format and sort correctly
     $.fn.dataTable.moment( 'DD/MM/YYYY' );
     var dataTable = $(table).DataTable();
     
     //Selección de la fila
     $( "#" + table.id + ' tbody').on( 'click', 'tr', function () {
         if ( !$(this).hasClass('selected') ) {
        	dataTable.$('tr.selected').removeClass('selected');
            $(this).toggleClass('selected');
            if(onClickCallback){
            	var row = dataTable.rows('.selected').data()[0];
            	if( row ){
            		for ( var i = 0 ; i < row.length ; i++ ){
            			row[i] = unEntity( row[i] );
            		}
            	}
            	onClickCallback ( row ); 
            }
         }
     } );
     
     return dataTable;
}

//Transform chars like: &lt; in <
function unEntity(str){
   return $("<textarea></textarea>").html(str).text();
}