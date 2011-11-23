/**
 *  Copyright 2011 Zuse Institute Berlin
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package de.zib.scalaris;

import com.ericsson.otp.erlang.OtpErlangList;

/**
 * Generic result list.
 *
 * @author Nico Kruber, kruber@zib.de
 * @version 3.8
 * @since 3.5
 */
public abstract class ResultList {
    protected OtpErlangList results = new OtpErlangList();

    /**
     * Default constructor.
     *
     * @param results  the raw results list as returned by scalaris.
     */
    protected ResultList(final OtpErlangList results) {
        this.results = results;
    }

    /**
     * Gets the number of results in the list.
     *
     * @return total number of results
     */
    public int size() {
        return results.arity();
    }

    /**
     * Processes the result at the given position which originated from a read
     * request and returns the value that has been read.
     *
     * @param pos
     *            the position in the result list (starting at 0)
     *
     * @return the stored value
     *
     * @throws TimeoutException
     *             if a timeout occurred while trying to fetch the value
     * @throws NotFoundException
     *             if the requested key does not exist
     * @throws UnknownException
     *             if any other error occurs
     * @throws UnsupportedOperationException
     *             if the operation is unsupported
     */
    public ErlangValue processReadAt(final int pos) throws TimeoutException,
            NotFoundException, UnknownException, UnsupportedOperationException {
        return new ErlangValue(
                CommonErlangObjects.processResult_read(results.elementAt(pos)));
    }

    /**
     * Processes the result at the given position which originated from
     * a write request.
     *
     * @param pos
     *            the position in the result list (starting at 0)
     *
     * @throws TimeoutException
     *             if a timeout occurred while trying to write the value
     * @throws UnknownException
     *             if any other error occurs
     * @throws UnsupportedOperationException
     *             if the operation is unsupported
     */
    public void processWriteAt(final int pos) throws TimeoutException,
            UnknownException, UnsupportedOperationException {
        CommonErlangObjects.processResult_write(results.elementAt(pos));
    }

    /**
     * Processes the result at the given position which originated from
     * a set_change request.
     *
     * @param pos
     *            the position in the result list (starting at 0)
     *
     * @throws TimeoutException
     *             if a timeout occurred while trying to write the value
     * @throws NotAListException
     *             if the previously stored value was no list
     * @throws UnknownException
     *             if any other error occurs
     * @throws UnsupportedOperationException
     *             if the operation is unsupported
     *
     * @since 3.8
     */
    public void processSetChangeAt(final int pos) throws TimeoutException,
            NotAListException, UnknownException, UnsupportedOperationException {
        CommonErlangObjects.processResult_setChange(results.elementAt(pos));
    }

    /**
     * Processes the result at the given position which originated from
     * a number_add request.
     *
     * @param pos
     *            the position in the result list (starting at 0)
     *
     * @throws TimeoutException
     *             if a timeout occurred while trying to write the value
     * @throws NotANumberException
     *             if the previously stored value was not a number
     * @throws UnknownException
     *             if any other error occurs
     * @throws UnsupportedOperationException
     *             if the operation is unsupported
     *
     * @since 3.8
     */
    public void processNumberAddAt(final int pos) throws TimeoutException,
            NotANumberException, UnknownException, UnsupportedOperationException {
        CommonErlangObjects.processResult_numberAdd(results.elementAt(pos));
    }

    /**
     * Processes the result at the given position which originated from
     * a number_add request.
     *
     * @param pos
     *            the position in the result list (starting at 0)
     *
     * @throws TimeoutException
     *             if a timeout occurred while trying to fetch/write the value
     * @throws NotFoundException
     *             if the requested key does not exist
     * @throws KeyChangedException
     *             if the key did not match <tt>old_value</tt>
     * @throws UnknownException
     *             if any other error occurs
     * @throws UnsupportedOperationException
     *             if the operation is unsupported
     *
     * @since 3.8
     */
    public void processTestAndSetAt(final int pos) throws TimeoutException,
            NotFoundException, KeyChangedException, UnknownException,
            UnsupportedOperationException {
        CommonErlangObjects.processResult_testAndSet(results.elementAt(pos));
    }

    /**
     * Processes the result at the given position which originated from
     * a commit request.
     *
     * @param pos
     *            the position in the result list (starting at 0)
     *
     * @throws TimeoutException
     *             if a timeout occurred while trying to write the value
     * @throws AbortException
     *             if the commit failed
     * @throws UnknownException
     *             if any other error occurs
     * @throws UnsupportedOperationException
     *             if the operation is unsupported, e.g. in a
     *             {@link TransactionSingleOp}
     */
    public void processCommitAt(final int pos) throws TimeoutException,
            AbortException, UnknownException, UnsupportedOperationException {
        CommonErlangObjects.processResult_commit(results.elementAt(pos));
    }

    /**
     * Gets the raw results.
     * (for internal use only)
     *
     * @return results as returned by erlang
     */
    OtpErlangList getResults() {
        return results;
    }
}
