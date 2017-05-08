/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import javax.annotation.Resource;
 import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
 import javax.sql.DataSource;
 import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author PELIN
 */
@ManagedBean ( name="kitapBean" )
@SessionScoped
public class KitapBean {
private String isbn;
private String ad;
private String yazar_id;
private String basim_yili;
private String yayin_evi;
private String bulunacak_isim;
CachedRowSet rowSet=null;
private String silinecek_isbn;
    /**
     * Creates a new instance of KitapBean
     */
  DataSource dataSource;
    public KitapBean() {
        try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/addressbook");
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }
    
  public String getIsbn()
 {
 return isbn;
 } 

 
 public void setIsbn( String isbn )
 {
 this.isbn = isbn;
 } 
   public String getAd()
 {
 return ad;
 } 

 
 public void setAd( String ad )
 {
 this.ad = ad;
 } 


 public String getYazar_id()
 {
 return yazar_id;
 } 

 
 public void setYazar_id( String yazar_id )
 {
 this.yazar_id = yazar_id;
 } 

public String getBasim_yili()
 {
 return basim_yili;
 } 

 
 public void setBasim_yili( String basim_yili )
 {
 this.basim_yili = basim_yili;
 } 

 public String getYayin_evi()
 {
 return yayin_evi;
 } 

 
 public void setYayin_evi( String yayin_evi )
 {
 this.yayin_evi = yayin_evi;
 }

   public String getBulunacak_isim()
 {
 return bulunacak_isim;
 } 

 
 public void setBulunacak_isim( String bulunacak_isim )
 {
 this.bulunacak_isim = bulunacak_isim;
 } 
 
 
  public String getSilinecek_isbn()
 {
 return silinecek_isbn;
 } 

 
 public void setSilinecek_isbn( String silinecek_isbn )
 {
 this.silinecek_isbn = silinecek_isbn;
 } 
 
 public String ekle() throws SQLException
 {
 // check whether dataSource was injected by the server
 if ( dataSource == null )
 throw new SQLException( "Unable to obtain DataSource" );

 // obtain a connection from the connection pool
 Connection connection = dataSource.getConnection();

 // check whether connection was successful
 if ( connection == null )
 throw new SQLException( "Unable to connect to DataSource" );

 try
 {
 // create a PreparedStatement to insert a new address book entry
 PreparedStatement addEntry =
 connection.prepareStatement( "INSERT INTO kitaplar " +
 "(isbn,ad,yazar_id,basim_yili,yayin_evi)" +
 "VALUES ( ?, ?, ?, ?,? )" );

 // specify the PreparedStatement's arguments
 addEntry.setInt( 1, Integer.parseInt(getIsbn()) );
 addEntry.setString( 2, getAd() );
 addEntry.setInt( 3, Integer.parseInt(getYazar_id()) );
 addEntry.setInt( 4, Integer.parseInt(getBasim_yili()) );
 addEntry.setString( 5, getYayin_evi() );


addEntry.executeUpdate(); // insert the entry
 return "index1"; // go back to index1.xhtml home page
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 


 public ResultSet bul() throws SQLException
 {
 // check whether dataSource was injected by the server
 if ( dataSource == null )
 throw new SQLException( "Unable to obtain DataSource" );

 // obtain a connection from the connection pool
 Connection connection = dataSource.getConnection();

 // check whether connection was successful
 if ( connection == null )
 throw new SQLException( "Unable to connect to DataSource" );

 try
 {
 // create a PreparedStatement to insert a new address book entry
 PreparedStatement ps =
 connection.prepareStatement( "select kitaplar.ISBN,kitaplar.ad,yazarlar.ad as yazar_ad,yazarlar.soyad as yazar_soyad,"
         + "yazarlar.id,kitaplar.basim_yili, kitaplar.yayin_evi from kitaplar,yazarlar " +
         "where kitaplar.ad=?" );
 ps.setString( 1, getBulunacak_isim() );
 rowSet = new com.sun.rowset.CachedRowSetImpl();
 rowSet.populate( ps.executeQuery() );
return rowSet;
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 



 
 
 public String sil() throws SQLException
 {
 // check whether dataSource was injected by the server
 if ( dataSource == null ){ 
      throw new SQLException( "Unable to obtain DataSource" );
 }

 // obtain a connection from the connection pool
 Connection connection = dataSource.getConnection();

 // check whether connection was successful
 if ( connection == null )
 throw new SQLException( "Unable to connect to DataSource" );

 try
 {
 // create a PreparedStatement to insert a new address book entry
 PreparedStatement deleteEntry =
 connection.prepareStatement( "delete from kitaplar where isbn=?");

 // specify the PreparedStatement's arguments
 deleteEntry.setInt( 1, Integer.parseInt(getSilinecek_isbn()) );
 
deleteEntry.executeUpdate(); // insert the entry
 return "index1"; // go back to index.xhtml page
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 


 

}

