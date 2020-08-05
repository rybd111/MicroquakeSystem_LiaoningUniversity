/*
 * MATLAB Compiler: 6.6 (R2018a)
 * Date: Wed Aug  5 10:52:58 2020
 * Arguments: 
 * "-B""macro_default""-W""java:PSO,pso_locate""-T""link:lib""-d""I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\PSO\\for_testing""class{pso_locate:I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mubiao3.m,I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mysort3.m,I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\PSO.m}""-a""I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mubiao3.m""-a""I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mysort3.m""-a""I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\PSO.m"
 */

package PSO;

import com.mathworks.toolbox.javabuilder.pooling.Poolable;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The <code>pso_locateRemote</code> class provides a Java RMI-compliant interface to 
 * MATLAB functions. The interface is compiled from the following files:
 * <pre>
 *  I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mubiao3.m
 *  I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\mysort3.m
 *  I:\\研究生阶段\\矿山\\理论算法\\粒子群算法\\定位精度研究-8.4\\PSO.m
 * </pre>
 * The {@link #dispose} method <b>must</b> be called on a <code>pso_locateRemote</code> 
 * instance when it is no longer needed to ensure that native resources allocated by this 
 * class are properly freed, and the server-side proxy is unexported.  (Failure to call 
 * dispose may result in server-side threads not being properly shut down, which often 
 * appears as a hang.)  
 *
 * This interface is designed to be used together with 
 * <code>com.mathworks.toolbox.javabuilder.remoting.RemoteProxy</code> to automatically 
 * generate RMI server proxy objects for instances of PSO.pso_locate.
 */
public interface pso_locateRemote extends Poolable
{
    /**
     * Provides the standard interface for calling the <code>mubiao3</code> MATLAB 
     * function with 3 input arguments.  
     *
     * Input arguments to standard interface methods may be passed as sub-classes of 
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of any 
     * supported Java type (i.e. scalars and multidimensional arrays of any numeric, 
     * boolean, or character type, or String). Arguments passed as Java types are 
     * converted to MATLAB arrays according to default conversion rules.
     *
     * All inputs to this method must implement either Serializable (pass-by-value) or 
     * Remote (pass-by-reference) as per the RMI specification.
     *
     * No usage documentation is available for this function.  (To fix this, the function 
     * author should insert a help comment at the beginning of their MATLAB code.  See 
     * the MATLAB documentation for more details.)
     *
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     *
     * @return Array of length nargout containing the function outputs. Outputs are 
     * returned as sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>. 
     * Each output array should be freed by calling its <code>dispose()</code> method.
     *
     * @throws java.rmi.RemoteException An error has occurred during the function call or 
     * in communication with the server.
     */
    public Object[] mubiao3(int nargout, Object... rhs) throws RemoteException;
    /**
     * Provides the standard interface for calling the <code>mysort3</code> MATLAB 
     * function with 5 input arguments.  
     *
     * Input arguments to standard interface methods may be passed as sub-classes of 
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of any 
     * supported Java type (i.e. scalars and multidimensional arrays of any numeric, 
     * boolean, or character type, or String). Arguments passed as Java types are 
     * converted to MATLAB arrays according to default conversion rules.
     *
     * All inputs to this method must implement either Serializable (pass-by-value) or 
     * Remote (pass-by-reference) as per the RMI specification.
     *
     * No usage documentation is available for this function.  (To fix this, the function 
     * author should insert a help comment at the beginning of their MATLAB code.  See 
     * the MATLAB documentation for more details.)
     *
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     *
     * @return Array of length nargout containing the function outputs. Outputs are 
     * returned as sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>. 
     * Each output array should be freed by calling its <code>dispose()</code> method.
     *
     * @throws java.rmi.RemoteException An error has occurred during the function call or 
     * in communication with the server.
     */
    public Object[] mysort3(int nargout, Object... rhs) throws RemoteException;
    /**
     * Provides the standard interface for calling the <code>PSO</code> MATLAB function 
     * with 2 input arguments.  
     *
     * Input arguments to standard interface methods may be passed as sub-classes of 
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of any 
     * supported Java type (i.e. scalars and multidimensional arrays of any numeric, 
     * boolean, or character type, or String). Arguments passed as Java types are 
     * converted to MATLAB arrays according to default conversion rules.
     *
     * All inputs to this method must implement either Serializable (pass-by-value) or 
     * Remote (pass-by-reference) as per the RMI specification.
     *
     * Documentation as provided by the author of the MATLAB function:
     * <pre>
     * % sample=[
     * % 4596627.472,41516836.655,21.545,2.13;%蒿子屯S1
     * % 4595913.485,41519304.125,23.921,2.28;%工业广场Z2
     * % 4595388.504,41518099.807,22.776,2.32;%杨甸子T4
     * % 4597983.404,41520207.356,22.661,2.36;%北青堆子W6
     * % 4594304.927,41518060.298,21.926,2.46;%树碑子U3
     * % 4593163.619,41516707.440,22.564,2.61;%南风井V7
     * %     ];
     * % sample=[
     * %      41517290.037,4599537.326,24.565,0; %大鹏R2
     * %      41519304.125,4595913.485,23.921,0.08;%工业广场Z1  7.11—8
     * %      41518060.298,4594304.927,21.926,0.23;%树碑子U3
     * %      41520207.356,4597983.404,22.661,0.099999;%北青堆子W4
     * % %      41520815.875,4597384.576,25.468,2.73;%矿上车队X5
     * %      41519926.476,4597275.978,20.705,0.08999 %火药库Y6
     * % %      41518099.807,4595388.504,22.776,0;%杨甸子T4
     * % %      41516836.655,4596627.472,21.545,0;%蒿子屯S??
     * % %      41516707.440,4593163.619,22.564,2.59;%南风井V??
     * %       ];
     * % del=sample(1,:);
     * % for i=1:length(sample(1,:))
     * %     sample(:,i)=sample(:,i)-sample(1,i);
     * % end
     * % v=3850;%系统速度
     * </pre>
     *
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     *
     * @return Array of length nargout containing the function outputs. Outputs are 
     * returned as sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>. 
     * Each output array should be freed by calling its <code>dispose()</code> method.
     *
     * @throws java.rmi.RemoteException An error has occurred during the function call or 
     * in communication with the server.
     */
    public Object[] PSO(int nargout, Object... rhs) throws RemoteException;
  
    /** 
     * Frees native resources associated with the remote server object 
     * @throws java.rmi.RemoteException An error has occurred during the function call or in communication with the server.
     */
    void dispose() throws RemoteException;
}
